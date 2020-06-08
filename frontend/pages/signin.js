import React from 'react';
import Layout from "../componentes/Layout";
import Link from "next/link";

const Signin = () => {
  return (
      <Layout>
        <h2>Signin page</h2>
        <Link href="/">Home</Link>
      </Layout>
  );
};

export default Signin;
