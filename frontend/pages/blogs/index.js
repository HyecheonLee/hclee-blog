import Layout from '../../componentes/Layout'
import React from 'react'
import {listBlogsWithCategoriesAndTags} from "../../actions/blog";
import Card from "../../componentes/blog/Card";
import Link from "next/link";

const Blogs = ({blogs, categories, tags, size}) => {
  const showAllCategories = () => {
    return categories.map((c, i) => {
      return (<Link href={`/categories/${c.slug}`} key={i}>
        <a className="btn btn-primary mr-1 ml-1 mt-3">{c.name}</a>
      </Link>);
    });
  }
  const showAllTags = () => {
    return tags.map((t, i) => {
      return (<Link href={`/tags/${t.slug}`} key={i}>
        <a className="btn btn-primary btn-outline-primary mr-1 ml-1 mt-3">{t.name}</a>
      </Link>);
    });
  }
  const showAllBlogs = () => {
    return blogs.map((blog, i) => {
      return (<article key={i}>
        <Card blog={blog}/>
        <hr/>
      </article>);
    });
  }

  return (
      <>
        <Layout>
          <main>
            <div className="container-fluid">
              <header>
                <div className="col-md-12 pt-3">
                  <h1 className="display-4 font-weight-bold text-center">Programming
                    blogs
                    and tutorials</h1>
                </div>
                <section>
                  <div className="pb-5 text-center">
                    {showAllCategories()}
                    <br/>
                    {showAllTags()}
                  </div>
                </section>
              </header>
            </div>
            <div className="container-fluid">
              <div className="row">
                <div className="col-md-12">{showAllBlogs()}</div>
              </div>
            </div>
          </main>
        </Layout>
      </>
  );
};

Blogs.getInitialProps = () => {
  return listBlogsWithCategoriesAndTags().then(data => {
    if (data.error) {
      console.log(data.error);
    } else {
      return {
        blogs: data.blogs,
        categories: data.categories,
        tags: data.tags,
        size: data.size
      }
    }
  })
}

export default Blogs; // getInitialProps