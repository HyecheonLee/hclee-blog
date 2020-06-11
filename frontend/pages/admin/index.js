import React from "react";
import Layout from "../../componentes/Layout";
import Admin from "../../componentes/auth/Admin";

const AdminIndex = () => {
  return (
      <Layout>
        <Admin>
          <h2>Admin Dashboard</h2>
        </Admin>
      </Layout>
  )
}

export default AdminIndex