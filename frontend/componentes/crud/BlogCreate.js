import React, {useState} from 'react';
import dynamic from "next/dynamic";
import 'react-quill/dist/quill.snow.css';
import {withRouter} from "next/router";

const ReactQuill = dynamic(() => import('react-quill'), {ssr: false})

const BlogCreate = ({router}) => {
  const [body, setBody] = useState({});
  const [values, setValues] = useState({
    error: "",
    sizeError: "",
    success: "",
    formData: "",
    title: "",
    hidePublishButton: false
  });
  const {error, sizeError, success, formData, title, hidePublishButton} = values;
  const publishBlog = (e) => {
    e.preventDefault()
    console.log('ready to publishBlog')
  }
  const handleChange = name => e => {
    console.log(e.target.value);
  }
  const handleBody = e => {
    console.log(e);
  }
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
            <ReactQuill value={body}
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
      <div>
        {createBlogForm()}
      </div>
  );
};

export default withRouter(BlogCreate);
