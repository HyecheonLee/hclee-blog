import {
  Collapse,
  Nav,
  Navbar,
  NavbarToggler,
  NavItem,
  NavLink
} from 'reactstrap';
import {useState} from "react";
import {APP_NAME} from "../config";
import Link from "next/link";
import {isAuth, signout} from "../actions/auth";
import Router from "next/router";
import NProgress from 'nprogress';
import 'nprogress/nprogress.css'

Router.onRouteChangeStart = url => NProgress.start()
Router.onRouteChangeComplete = url => NProgress.done()
Router.onRouteChangeError = url => NProgress.done()

const Header = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggle = () => setIsOpen(!isOpen);

  return (
      <div>
        <Navbar color="light" light expand="md">
          <Link href={"/"}>
            <NavLink className="font-weight-bold">{APP_NAME}</NavLink>
          </Link>
          <NavbarToggler onClick={toggle}/>
          <Collapse isOpen={isOpen} navbar>
            <Nav className="ml-auto" navbar>
              {isAuth() &&
              <NavItem>
                <NavLink style={{cursor: "pointer"}} onClick={() =>
                    signout(
                        () => Router.replace(
                            `/signin`))}>signout</NavLink>
              </NavItem>}
              {isAuth() && isAuth().role === "ROLE_USER" && (
                  <NavItem>
                    <Link href="/user">
                      <NavLink>{`${isAuth().name}'s Dashboard`}</NavLink>
                    </Link>
                  </NavItem>
              )}
              {isAuth() && isAuth().role === "ROLE_ADMIN" && (
                  <NavItem>
                    <Link href="/admin">
                      <NavLink>{`${isAuth().name}'s Dashboard`}</NavLink>
                    </Link>
                  </NavItem>
              )}
              <>
                <NavItem>
                  <Link href="/blogs">
                    <NavLink>Blogs</NavLink>
                  </Link>
                </NavItem>
              </>
            </Nav>
          </Collapse>
        </Navbar>
      </div>
  )
}
export default Header