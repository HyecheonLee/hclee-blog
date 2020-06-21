import React, {useEffect, useState} from 'react';
import dynamic from "next/dynamic";
import 'react-quill/dist/quill.snow.css';
import {withRouter} from "next/router";
import {getCategories} from "../../actions/category";
import {getTags} from "../../actions/tag";
import {getCookie} from "../../actions/auth";
import {createBlog} from "../../actions/blog";
import {QuillFormats, QuillModules} from "../../helpers/quill";

const ReactQuill = dynamic(() => import('react-quill'), {ssr: false})

const BlogCreate = ({router}) => {
  const blogFromLS = () => {
    if (typeof window === 'undefined') {
      return false
    }
    if (localStorage.getItem('blog')) {
      return JSON.parse(localStorage.getItem('blog'))
    } else {
      return false;
    }
  };
  const [categories, setCategories] = useState([])
  const [tags, setTags] = useState([])

  const [checked, setChecked] = useState([]);//categories
  const [checkedTag, setCheckedTag] = useState([]);//tags

  const [body, setBody] = useState(blogFromLS());
  const [values, setValues] = useState({
    error: "",
    sizeError: "",
    success: "",
    formData: undefined,
    title: "",
    hidePublishButton: false
  });

  const {error, sizeError, success, formData, title, hidePublishButton} = values;
  const token = getCookie('token');

  useEffect(() => {
    let formData = new FormData();
    setValues({...values, formData: formData})
    initCategories();
    initTags();
  }, [router])

  const initCategories = () => {
    getCategories()
    .then(data => {
      if (data.error) {
        setValues({...values, error: data.error})
      } else {
        setCategories(data)
      }
    })
  };

  const initTags = () => {
    getTags().then(data => {
      if (data.error) {
        setValues({...values, error: data.error});
      } else {
        setTags(data)
      }
    })
  };

  const publishBlog = (e) => {
    e.preventDefault()
    // console.log('ready to publishBlog')
    createBlog(formData, token)
    .then(data => {
      console.log(data);
      if (data.error) {
        setValues({...values, error: data.error});
      } else {
        setValues(
            {
              ...values,
              title: '',
              error: '',
              success: `A new blog titled "${data.title}" is created`
            });
        setBody('');
        setCategories([]);
        setTags([]);
      }
    });
  }

  const handleChange = name => e => {
    // console.log(e.target.value);
    const value = name === 'photo' ? e.target.files[0] : e.target.value
    // let formData = new FormData();
    console.log(formData);
    if (formData) {
      formData.set(name, value);
    } else {
      new FormData()
    }
    setValues({...values, [name]: value, formData, error: ''});
  }
  const handleBody = e => {
    // console.log(e);
    setBody(e)
    formData.set('body', e)
    if (typeof window !== 'undefined') {
      localStorage.setItem('blog', JSON.stringify(e))
    }
  };

  const handleToggle = (c) => () => {
    setValues({...values, error: ''})
    //return the first index or -1
    const clickedCategory = checked.indexOf(c)
    const all = [...checked]
    if (clickedCategory === -1) {
      all.push(c)
    } else {
      all.splice(clickedCategory, 1)
    }
    setChecked(all);
    formData.set('categories', all);
  }
  const handleTagsToggle = (t) => () => {
    setValues({...values, error: ''})
    //return the first index or -1
    const clickedTags = checked.indexOf(t)
    const all = [...checkedTag]

    if (clickedTags === -1) {
      all.push(t)
    } else {
      all.splice(clickedTags, 1)
    }
    console.log(all);
    setCheckedTag(all);
    formData.set('tags', all);
  }

  const showCategories = () => {
    return (
        categories && categories.map((c, i) => (
            <li key={`$category_${i}`} className="list-unstyled">
              <label className="form-check-label">
                <input onChange={handleToggle(c._id)}
                       className="mr-2"
                       type="checkbox"/>
                {c.name}
              </label>
            </li>
        ))
    )
  }
  const showTags = () => {
    return (
        tags && tags.map((t, i) => (
            <li key={`tag_${i}`} className="list-unstyled">
              <label className="form-check-label">
                <input onChange={handleTagsToggle(t._id)} className="mr-2"
                       type="checkbox"/>
                {t.name}</label>
            </li>
        ))
    )
  }
  const showError = () => (
      <div className="alert alert-danger"
           style={{display: error ? '' : 'none'}}>
        {error}
      </div>
  )
  const showSuccess = () => (
      <div className="alert alert-success"
           style={{display: success ? '' : 'none'}}>
        {success}
      </div>
  )

  const createBlogForm = () => {
    return (
        <form onSubmit={publishBlog}>
          <div className="form-group">
            <label className="text-muted">Title</label>
            <input type="text" className="form-control"
                   value={title}
                   onChange={handleChange('title')}/>
          </div>
          <div className="form-group">
            <ReactQuill
                modules={QuillModules}
                formate={QuillFormats}
                value={body}
                theme="snow"
                placeholder={"Write something amazing..."}
                onChange={handleBody}
            />
          </div>
          <div>
            <button className="btn btn-primary">Publish</button>
          </div>
        </form>
    );
  }
  return (
      <div className="container-fluid pb-5">
        <div className="row">
          <div className="col-md-8">
            {createBlogForm()}
            <div className="pt-3">
              {showError()}
              {showSuccess()}
            </div>
          </div>
          <div className="col-md-4">
            <div>
              <div className="form-group pb-2">
                <h5>Featured image</h5>
                <hr/>
                <small className="text-muted">Max size: 1mb</small><br/>
                <label className="btn btn-outline-info">Upload featured
                  image
                  <input onChange={handleChange('photo')}
                         type="file"
                         accept="image/*" hidden/>
                </label>
              </div>
            </div>
            <div>
              <h5>Categories</h5>
              <hr/>
              <ul style={{
                maxHeight: '100px',
                overflowY: 'scroll'
              }}>{showCategories()}</ul>
            </div>
            <div>
              <h5>Tags</h5>
              <hr/>
              <ul style={{
                maxHeight: '100px',
                overflowY: 'scroll'
              }}>
                {showTags()}
              </ul>
            </div>
          </div>
        </div>
      </div>
  );
};

export default withRouter(BlogCreate);
