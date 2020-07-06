import Layout from "../componentes/Layout";
import React from "react";
import Link from "next/link";

const Index = () => {
  return (
      <Layout>
        <h2>Index</h2>
        <Link href="/signup">
          <a>Signup</a>
        </Link>
      </Layout>
  )
}

export default Index