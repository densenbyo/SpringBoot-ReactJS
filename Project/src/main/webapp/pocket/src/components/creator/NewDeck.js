import React, {Component} from "react";
import Alert from "../../services/util/Alert";
import RepoService from "../../services/repoService"
import {Button, Card, Container, Form, Row} from "react-bootstrap";

export default class NewDeck extends Component {
    constructor(props) {
        super(props);
        this.state = this.initState;
        this.deckChange = this.deckChange.bind(this);
        this.create = this.create.bind(this);
    }

    initState = {
        name:"",
        description:"",
        isPublic: false
    }

    create(event){
        event.preventDefault();
        const id = localStorage.getItem("userID");
        RepoService.addNewDeck(this.state.name, this.state.description, this.state.isPublic)
            .then(() => {
                this.setState({"show": true});
                setTimeout(() => this.setState({"show":false}), 3000);
            })
    }

    deckChange(event){
        this.setState({
            [event.target.name]:event.target.value
        });
        console.log(this.state);
        console.log(this.state.isPublic)
    };

    render() {
        const{name, description} = this.state;

        return (
            <div>
                <div style={{"display":this.state.show ? "block" : "none"}}>
                    <Alert children={{show: this.state.show, message: "Deck added successfully."}}/>
                </div>
                <Container>
                    <Row className={" justify-content-md-center"}>
                        <Card className={"border border-dark bg-dark text-white"} >
                            <Form onSubmit={this.create}>
                                <Card.Header>New Deck</Card.Header>
                                <Card.Body>
                                    <Form.Group className="mb-3">
                                        <Form.Label>Deck Name</Form.Label>
                                        <Form.Control value={name} name="name" type={"text"} required placeholder="Name"
                                                      autoComplete={"off"} onChange={this.deckChange}/>
                                    </Form.Group>
                                    <Form.Group>
                                        <Form.Label>Deck Description</Form.Label>
                                        <Form.Control value={description} name="description" type={"text"} required placeholder="Description"
                                                      autoComplete={"off"} onChange={this.deckChange}/>
                                    </Form.Group>
                                    <Form.Group>
                                        <Form.Check type={"checkbox"}  name="isPublic" label={"Is Public"}
                                                    onChange={() => {
                                                        this.setState(
                                                            {isPublic: !this.state.isPublic}
                                                        )}} defaultChecked={this.state.isPublic}/>
                                    </Form.Group>
                                    <Button size="sm" variant="primary" type="submit">
                                        Add Deck
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