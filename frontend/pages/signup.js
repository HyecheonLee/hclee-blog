import React from 'react';
import Layout from "../componentes/Layout";
import SignupComponent from "../componentes/auth/SignupComponent";

const SignUp = () => {
  return (
      <Layout>
        <h2 className="text-center pt-4 ">Signup page</h2>
        <div className="row">
          <div className="col-md-8 offset-md-3">
            <SignupComponent/>
          </div>
        </div>
      </Layout>
  );
};

export default SignUp;
