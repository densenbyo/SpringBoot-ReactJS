import React from 'react';
import {Button, Container, Nav, Navbar} from 'react-bootstrap';
import {Link} from 'react-router-dom';

import AuthService from "../services/authService";

export default class NavigationBar extends React.Component {

    render() {
        if(localStorage.length !== 0){
            return (
                <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
                    <Container>
                        <Link to={"/"} className = "navbar-brand">
                            <Navbar.Brand>Pocket Lingo</Navbar.Brand>
                        </Link>
                        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                        <Navbar.Collapse id="responsive-navbar-nav">
                            <Nav className="me-auto">
                                <Link to={"repo"} className="nav-link">Repo</Link>
                                <Link to={"cards"} className="nav-link">Cards</Link>
                                <Link to={"find"} className="nav-link">Find</Link>
                            </Nav>
                            <Nav className="navbar-right">
                                <Button className="nav-link" onClick={AuthService.logout} variant={"outline-dark"}>Logout</Button>
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
            );
        }else{
            return (
                <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
                    <Container>
                        <Link to={"/"} className = "navbar-brand">
                            <Navbar.Brand>Pocket Lingo</Navbar.Brand>
                        </Link>
                        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                        <Navbar.Collapse id="responsive-navbar-nav">
                            <Nav className="navbar-right">
                                <Link to={"login"} className="nav-link">Login</Link>
                                <Link to={"registration"} className="nav-link">Registration</Link>
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
            );
        }
    }
}