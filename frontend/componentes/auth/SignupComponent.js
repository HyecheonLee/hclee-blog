import React, {useState} from 'react';
import {signup} from "../../actions/auth"

const SignupComponent = () => {
  const [values, setValues] = useState({
    username: "",
    email: "",
    password: "",
    error: false,
    loading: false,
    message: "",
    showForm: true
  });
  const {name, email, password, error, loading, message, showForm} = values;
  const handleSubmit = (e) => {
    e.preventDefault();
    console.table({name, email, password, error, loading, message, showForm});
    setValues({
      ...values, loading: true, error: false
    });
    const user = {name, email, password}
    signup(user)
    .then(data => {
      if (data.error) {
        setValues({...values, error: data.error, loading: false})
      } else {
        setValues({
          ...values,
          name: '',
          email: '',
          password: '',
          error: '',
          loading: false,
          message: data.message,
          showForm: false
        });
      }
    });

  }
  const handleChange = name => (e) => {
    setValues({
      ...values,
      error: false,
      [name]: e.target.value
    })
    console.log(name, e.target.value)
  }
  const showLoading = () => loading ?
      <div className="alert alert-info">Loading...</div> : ""
  const showError = () => error ?
      <div className="alert alert-danger">{error}</div> : ""
  const showMessage = () => message ?
      <div className="alert alert-success">{message}</div> : ""

  const signupForm = () => {
    return (
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <input onChange={handleChange('name')}
                   type="text"
                   placeholder="Type your name"
                   className="form-control"
                   value={name}
            />
          </div>
          <div className="form-group">
            <input onChange={handleChange('email')}
                   type="email"
                   placeholder="Type your email"
                   className="form-control"
                   value={email}
            />
          </div>
          <div className="form-group">
            <input onChange={handleChange('password')}
                   type="password"
                   placeholder="Type your password"
                   className="form-control"
                   value={password}
            />
          </div>
          <div>
            <button className="btn btn-primary">Signup</button>
          </div>
        </form>
    )
  }
  return (
      <>
        {showError()}
        {showLoading()}
        {showMessage()}
        {showForm && signupForm()}
      </>
  );
};

export default SignupComponent;
