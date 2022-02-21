import React, {Component} from 'react';
import {Card, Table, ButtonGroup, Button, InputGroup, FormControl} from 'react-bootstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import AuthService from "../../services/authService";

export default class FindByName extends Component {

    constructor(props) {
        super(props);
        this.state = this.initState;
        this.searchChange = this.searchChange.bind(this);
        this.findBySearch = this.findBySearch.bind(this);
    }

    initState = {
        getresult : [],
        search : "",
    }


    findBySearch() {
        if (localStorage.length !== 0){
            var resData;
            axios.get("http://localhost:8080/users/find/findByUsername/"+this.state.search, {
                headers: {
                    'Authorization' : 'Bearer ' + localStorage.getItem("token")
                }
            })
                .then((response) => {
                    console.log("GET response");
                    console.log(response.data)
                    resData = response.data;
                    this.setState({getresult:resData});
                    console.log(this.state.getresult)
                }).catch(error => console.log(error));
        }
    }

    searchChange = event =>{
        this.setState({
            [event.target.name] : event.target.value
        });
    }

    cancelSearch = () =>{
        this.setState({"search" : ""})
    }

    render() {
        const {search} = this.state;
        const map = this.state.getresult;

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
                        <div style={{"float":"left", "marginTop":"15px"}}>
                            <InputGroup size={"sm"} className="mb-3" >
                                <FormControl
                                    placeholder="Search by username"
                                    name={"search"}
                                    value={search}
                                    onChange={this.searchChange}
                                    disabled={localStorage.length === 0}
                                />
                                <Button type={"submit"} variant="primary"
                                        id="button-addon2" size={"sm"}
                                        onClick={this.findBySearch}
                                        disabled={localStorage.length === 0}>Search</Button>
                                <Button variant="danger" id="button-addon2"
                                        size={"sm"} onClick={this.cancelSearch}
                                        disabled={localStorage.length === 0}>Clear</Button>
                            </InputGroup>
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
                            {
                                map === [] || localStorage.length === 0?
                                    <tr align={"center"}>
                                        <td colSpan={"6"}>No user.</td>
                                    </tr> :
                                 (
                                <tr key={map.id}>
                                    <td>{map.username}</td>
                                    <td>{map.mail}</td>
                                    <td>{map.role}</td>
                                    {localStorage.getItem("user") === 'ROLE_ADMIN' && map.length !== 0 ?
                                        <td>
                                            <ButtonGroup>
                                                <Button size={"sm"} variant={"outline-danger"} onClick={() => AuthService.setRemoved(search)}>Del</Button>
                                            </ButtonGroup>
                                        </td> :
                                        <td/>
                                    }
                                </tr>
                                )
                            }
                            </tbody>
                        </Table>
                    </Card.Body>
                </Card>
            </div>
        );
    }
}