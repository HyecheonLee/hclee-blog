import React from 'react';
import Layout from "../componentes/Layout";
import Link from "next/link";

const SignUp = () => {
  return (
      <Layout>
        <h2>Signup page</h2>
        <Link href="/">Home</Link>
      </Layout>
  );
};

export default SignUp;
