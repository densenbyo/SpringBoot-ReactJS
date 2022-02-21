import React, {Component} from 'react';
import {Card, Table, ButtonGroup, Button} from 'react-bootstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import AuthService from "../../services/authService"

export default class Find extends Component {

    constructor(props) {
        super(props);
        this.state = {
            allusers: []
        };
    }

    componentDidMount() {
        this.findAllUsers();
        setTimeout(()=>console.log(this.state.allusers), 3000);
    }

    findAllUsers() {
        if(localStorage.getItem("user") === 'ROLE_ADMIN'){
            axios.get("http://localhost:8080/users/all", {
                headers: {
                    'Authorization' : 'Bearer ' + localStorage.getItem("token")
                }
            })
                .then(response => response.data)
                .then((data) => {
                    console.log("GET response");
                    this.setState({allusers:data});
                }).catch(error => console.log(error));
        }
    }

    render() {
        return (
            <div>
                <Card className={"border border-dark bg-dark text-white"}>
                    <Card.Header>
                        <div>
                            <ButtonGroup aria-label="Basic example">
                                <Link to={"/find"} className={"btn-secondary btn rounded"}>All Users</Link>
                                <Link to={"/find/findById"} className={"btn-secondary btn rounded"}>By ID</Link>
                                <Link to={"/find/findByName"} className={"btn-secondary btn rounded"}>By Username</Link>
                                <Link to={"/find/findByMail"} className={"btn-secondary btn rounded"}>By Mail</Link>
                            </ButtonGroup>
                        </div>
                    </Card.Header>
                    <Card.Body>
                        <Table bordered hover striped variant="dark">
                            <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.allusers === [] || localStorage.getItem("user") !== 'ROLE_ADMIN' ?
                                    <tr align={"center"}>
                                        <td colSpan={"6"}>No users.</td>
                                    </tr>
                                     :
                                    this.state.allusers && this.state.allusers.map((user) => (
                                        <tr key={user.id}>
                                            <td>{user.username}</td>
                                            <td>{user.mail}</td>
                                            <td>{user.role}</td>
                                            <td>
                                                <ButtonGroup>
                                                    <Button size={"sm"} variant={"outline-danger"}
                                                            onClick={()=>AuthService.setRemoved(user.id)}>Delete</Button>
                                                </ButtonGroup>
                                            </td>
                                        </tr>
                                    ))
                                }
                            </tbody>
                        </Table>
                    </Card.Body>
                </Card>
            </div>
        );
    }
}