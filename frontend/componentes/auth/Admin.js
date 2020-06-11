import React, {useEffect} from 'react';
import {isAuth} from "../../actions/auth";
import {Router} from "next/router";

const Admin = ({children}) => {
  useEffect(() => {
    if (!isAuth()) {
      Router.push("/signin")
    } else if (isAuth().role !== "ROLE_ADMIN") {
      Router.push("/")
    }
  }, [])
  return (
      <>
        {children}
      </>
  );
};

export default Admin;
