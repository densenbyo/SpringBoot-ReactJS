import React, {Component} from "react";
import Alert from "../../services/util/Alert";
import {Button, Card, Container, Form, Row} from "react-bootstrap";
import RepoService from "../../services/repoService";

export default class NewCard extends Component {
    constructor(props) {
        super(props);
        this.state = this.initState;
        this.cardChange = this.cardChange.bind(this);
        this.create = this.create.bind(this);
    }

    initState = {
        word : "",
        translation : ""
    }

    create(event){
        event.preventDefault();
        const id = localStorage.getItem("userID");
        RepoService.addNewCard(id, this.state.word, this.state.translation)
            .then(() => {
                this.setState({"show": true});
                setTimeout(() => this.setState({"show":false}), 3000);
            })
    }

    cardChange(event){
        this.setState({
            [event.target.name]:event.target.value
        });
        console.log(this.state);
    }

    render() {
        const {word, translation} = this.state;

        return (
            <div>
                <div style={{"display":this.state.show ? "block" : "none"}}>
                    <Alert children={{show: this.state.show, message: "Card added successfully."}}/>
                </div>
                <Container>
                    <Row className={" justify-content-md-center"}>
                        <Card className={"border border-dark bg-dark text-white"} >
                            <Form onSubmit={this.create}>
                                <Card.Header>New Card</Card.Header>
                                <Card.Body>
                                    <Form.Group className="mb-3">
                                        <Form.Label>Word</Form.Label>
                                        <Form.Control value={word} name="word" type={"text"} required placeholder="Word"
                                                      autoComplete={"off"} onChange={this.cardChange}/>
                                    </Form.Group>
                                    <Form.Group>
                                        <Form.Label>Translation</Form.Label>
                                        <Form.Control value={translation} name="translation" type={"text"} required placeholder="Translation"
                                                      autoComplete={"off"} onChange={this.cardChange}/>
                                    </Form.Group>
                                    <Button size="sm" variant="primary" type="submit">
                                        Add Card
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