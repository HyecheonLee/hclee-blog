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
              {isAuth() ?
                  <NavItem>
                    <NavLink style={{cursor: "pointer"}} onClick={() => signout(
                        () => Router.replace(`/signin`))}>signout</NavLink>
                  </NavItem> :
                  <>
                    <NavItem>
                      <Link href="/signin">
                        <NavLink>Signin</NavLink>
                      </Link>
                    </NavItem>
                    <NavItem>
                      <Link href="/signup">
                        <NavLink>Signup</NavLink>
                      </Link>
                    </NavItem>
                  </>
              }
            </Nav>
          </Collapse>
        </Navbar>
      </div>
  )
}
export default Header