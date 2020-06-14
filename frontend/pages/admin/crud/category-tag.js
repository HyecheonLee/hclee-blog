import React from "react";
import Layout from "../../../componentes/Layout";
import Admin from "../../../componentes/auth/Admin";
import Category from "../../../componentes/crud/Category";
import Tag from "../../../componentes/crud/Tag";

const CategoryTag = () => {
  return (
      <Layout>
        <Admin>
          <div className="container-fluid">
            <div className="row">
              <div className="col-md-12 pt-5 pb-5">
                <h2>Manage Categories and Tags</h2>
              </div>
              <div className="col-md-6">
                <Category/>
              </div>
              <div className="col-md-6">
                <Tag/>
              </div>
            </div>
          </div>
        </Admin>
      </Layout>
  )
}

export default CategoryTag