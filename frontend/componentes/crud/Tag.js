import React, {useEffect, useState} from 'react';
import {getCookie} from "../../actions/auth";
import {createTag, getTags, removeTag} from '../../actions/tag'

const Tag = () => {
  const [values, setValues] = useState({
    name: '',
    error: false,
    success: false,
    tags: [],
    removed: false,
    reload: false
  });
  const {name, error, success, tags, removed, reload} = values
  const token = getCookie('token')

  useEffect(() => {
    loadTags()
  }, [reload]);

  const loadTags = () => {
    getTags().then(data => {
      if (data && data.error) {
        console.log(data.error)
      } else {
        setValues({...values, tags: data})
      }
    })
  };

  const showTags = () => {
    return tags.map((tag, index) => {
      return <button title="Double click to delete"
                     onDoubleClick={() => deleteConfirm(tag.slug)}
                     className="btn btn-outline-primary mr-1 ml-1 mt-3">
        {tag.name}
      </button>
    });
  }
  const deleteConfirm = slug => {
    let answer = window.confirm(
        "Are you sure you want to delete this category?")
    if (answer) {
      deleteTag(slug, token)
    }
  }
  const deleteTag = slug => {
    removeTag(slug, token)
    .then(data => {
      if (data.error) {
        console.log(data.error)
      } else {
        setValues({
          ...values,
          error: false,
          success: false,
          name: "",
          removed: true,
          reload: !reload
        })
      }
    })
  }

  const clickSubmit = (e) => {
    e.preventDefault()
    createTag({name: name}, token)
    .then(data => {
      if (data.error) {
        setValues({...values, error: data.error, success: false})
      } else {
        setValues({
          ...values,
          error: false,
          success: true,
          removed: false,
          name: '',
          reload: !reload
        })
      }
    })
  }
  const handleChange = (e) => {
    setValues({
      ...values,
      name: e.target.value,
      error: false,
      success: false,
      removed: false
    })
  }
  const showSuccess = () => {
    if (success) {
      return <p className="text-success">Tag is created</p>
    }
  }
  const showError = () => {
    if (error) {
      return <p className="text-danger">Tag already exist</p>
    }
  }
  const showRemove = () => {
    if (removed) {
      return <p className="text-danger">Tag is removed</p>
    }
  }
  const mouseMoveHandler = e => {
    setValues({...values, error: false, success: false, removed: false})
  }

  const newTagForm = () => {
    return (
        <form onSubmit={clickSubmit}>
          <div className="form-group">
            <label className="text-muted">Name</label>
            <input type="text" className="form-control" onChange={handleChange}
                   value={name} required/>
          </div>
          <div>
            <button type="submit" className="btn btn-primary">Create</button>
          </div>
        </form>)
  }
  return (
      <>
        {showSuccess()}
        {showError()}
        {showRemove()}
        <div onMouseMove={mouseMoveHandler}>
          {newTagForm()}
          {showTags()}
        </div>
      </>
  )
}
export default Tag