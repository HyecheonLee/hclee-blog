import React from "react";
import Layout from "../../componentes/Layout";
import Private from "../../componentes/auth/Private";

const UserIndex = () => {
  return (
      <Layout>
        <Private>
          <h2>User Dashboard</h2>
        </Private>
      </Layout>
  )
}

export default UserIndex