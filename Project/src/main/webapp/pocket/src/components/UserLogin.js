import React, {Component} from 'react';
import {Button, Card, Container, Form, Row} from 'react-bootstrap';

import Alert from "../services/util/Alert";
import AuthService from "../services/authService"

export default class UserLogin extends Component {

    constructor(props) {
        super(props);
        this.state = this.initState;
        this.userChange = this.userChange.bind(this);
        this.login = this.login.bind(this);
    }

    initState = {
        username: "",
        password: "",
        role: ""
    };

    login(event) {
        event.preventDefault();
        AuthService.login(this.state.username, this.state.password)
            .then(() => {
                this.setState({"show": true});
                setTimeout(() => this.setState({"show":false}), 3000);
            });
    }

    userChange(event) {
        this.setState({
            [event.target.name]:event.target.value
        });
    };

    render() {
        const{username, password} = this.state;

        return (
            <div>
                <div style={{"display":this.state.show ? "block" : "none"}}>
                    <Alert children={{show: this.state.show, message: "Login went successfully."}}/>
                </div>
                <Container>
                    <Row className={" justify-content-md-center"}>
                        <Card className={"border border-dark bg-dark text-white"} style={{ width: '20rem' }}>
                            <Form onSubmit={this.login} id="login">
                                <Card.Header>
                                    Login
                                </Card.Header>
                                <a href="http://localhost:8080/oauth2/authorization/google">Sign-in Google</a>
                                <Card.Body>
                                    <Form.Group className="mb-3">
                                        <Form.Label>Username</Form.Label>
                                        <Form.Control required value={username} onChange={this.userChange}
                                                      name="username" type="text" placeholder="Username" />
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="formBasicPassword">
                                        <Form.Label>Password</Form.Label>
                                        <Form.Control required value={password} onChange={this.userChange}
                                                      name="password" type="password" placeholder="Password" />
                                    </Form.Group>
                                    <Button size="sm" variant="success" type="submit"
                                            disabled={this.state.username.length === 0 || this.state.password.length === 0}>
                                        Sign in
                                    </Button>
                                </Card.Body>
                            </Form>
                        </Card>
                    </Row>
                </Container>
            </div>
        );
    }
}