import React, {useEffect, useState} from 'react';
import {authenticate, isAuth, signin} from "../../actions/auth"
import Router from "next/router";

const SigninComponent = () => {
  const [values, setValues] = useState({
    email: "",
    password: "",
    error: false,
    loading: false,
    message: "",
    showForm: true
  });
  const {email, password, error, loading, message, showForm} = values;
  const handleSubmit = (e) => {
    e.preventDefault();
    console.table({email, password, error, loading, message, showForm});
    setValues({
      ...values, loading: true, error: false
    });
    const user = {email, password}
    signin(user)
    .then(data => {
      if (data.error) {
        setValues({...values, error: data.error, loading: false})
      } else {
        authenticate(data, () => {
          if (isAuth() && isAuth().role === "ROLE_ADMIN") {
            Router.push(`/admin`)
          } else {
            Router.push(`/user`)
          }
        });
      }
    });

  }
  useEffect(() => {
    isAuth() && Router.push(`/`)
  }, [message]);

  const handleChange = name => (e) => {
    setValues({
      ...values,
      error: false,
      [name]: e.target.value
    })
  }
  const showLoading = () => loading ?
      <div className="alert alert-info">Loading...</div> : ""
  const showError = () => error ?
      <div className="alert alert-danger">{error}</div> : ""
  const showMessage = () => message ?
      <div className="alert alert-success">{message}</div> : ""

  const signinForm = () => {
    return (
        <form onSubmit={handleSubmit}>
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
        {showForm && signinForm()}
      </>
  );
};

export default SigninComponent;
