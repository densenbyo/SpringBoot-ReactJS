import React, {Component} from 'react';
import {Button, Card, Container, Form, Row} from "react-bootstrap";

import Alert from "../../services/util/Alert";
import AuthService from "../../services/authService";

export default class UserRegistration extends Component {

    constructor(props) {
        super(props);
        this.state = this.initState;
        this.state.show = false;
        this.userChange = this.userChange.bind(this);
        this.registration = this.registration.bind(this);
    }

    initState = {
        username: "",
        email: "",
        password: ""
    };

    registration(event) {
        event.preventDefault();
        AuthService.register(this.state.username, this.state.email,this.state.password)
            .then(() => {
                this.setState({"show": true});
                setTimeout(() => this.setState({"show":false}), 3000);
            });
        this.setState(this.initState);
    }

    userChange(event) {
        this.setState({
            [event.target.name]:event.target.value
        });
    };

    render() {
        const{username, email, password} = this.state;

        return (
            <div>
                <div style={{"display":this.state.show ? "block" : "none"}}>
                    <Alert children={{show: this.state.show, message: "Registration went successfully."}}/>
                </div>
                <Container>
                    <Row className={" justify-content-md-center"}>
                        <Card className={"border border-dark bg-dark text-white"} style={{ width: '20rem' }}>
                            <Form onSubmit={this.registration} id="registration">
                                <Card.Header>Registration</Card.Header>
                                <Card.Body>
                                    <Form.Group className="mb-3" controlId="formGridUsername">
                                        <Form.Label>Username</Form.Label>
                                        <Form.Control required value={username} onChange={this.userChange}
                                                      name="username" type="text" placeholder="Username" />
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="formGridMail">
                                        <Form.Label>Email address</Form.Label>
                                        <Form.Control required value={email} onChange={this.userChange}
                                                      name="email" type="email" placeholder="Enter email" />
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="formGridPassword">
                                        <Form.Label>Password</Form.Label>
                                        <Form.Control required value={password} onChange={this.userChange}
                                                      name="password" type="password" placeholder="Password" />
                                    </Form.Group>
                                    <Button size="sm" variant="primary" type="submit"
                                            disabled={this.state.username.length === 0 || this.state.password.length === 0 || this.state.email === 0}>
                                        Sign up
                                    </Button>
                                </Card.Body>
                            </Form>
                        </Card>
                    </Row>
                </Container>
            </div>
        )
    }
}