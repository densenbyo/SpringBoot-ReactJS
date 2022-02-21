import React, {Component} from "react";
import {Accordion, Button, ButtonGroup, Card, ListGroup} from "react-bootstrap";
import RepoService from "../services/repoService";
import {Link} from "react-router-dom";

export default class Repo extends Component {
    constructor(props) {
        super(props);
        this.state = this.initState;
    }

    initState = {
        details:[],
        decks:[],
        cards:[]
    }

    componentDidMount() {
        setTimeout(() => (
            this.request(),
            this.getDecks(),
            this.getCards()),
        3000);
    }

    request(){
        const id = localStorage.getItem("userID");
        RepoService.getCurrentRepo(id)
            .then(response => response.data)
            .then((data)  => {
                this.setState({details:data});
            }).catch(error => console.log("SOOKQA KAKAYA TO OSHIBKA AOAOAOAOOA " + error));
    }

    getDecks(){
        const id = localStorage.getItem("userID");
        RepoService.getAllDecks(id)
            .then(response => response.data)
            .then((data) => {
                this.setState({decks:data});
                console.log(this.state.decks)
            }).catch(error => console.log(error));
    }

    getCards(){
        const id = localStorage.getItem("userID");
        RepoService.getAllCards(id)
            .then(response => response.data)
            .then((data) => {
                this.setState({cards:data});
                console.log(this.state.cards);
            })
    }

    onclick(e){
        const id = localStorage.getItem("repoID")
        RepoService.deleteDeck(id, e)
    }


    onclick2(e){
        const id = localStorage.getItem("repoID")
        RepoService.deleteCard(id, e)
    }

    render() {
        const arr = this.state.decks;
        const arr2 = this.state.cards;
        return (
            <div>
                <Card className={"border border-dark bg-dark text-white"} >
                    <Card.Header>
                        <h4>Repo</h4>
                        <p className="float-sm-start">ID - {localStorage.getItem("userID")}</p>
                        <ButtonGroup className={"float-sm-end"}>
                            <Link to={"/repo/newdeck"} className={"btn-primary btn rounded"}>Add deck</Link>
                            <Link to={"/repo/newcard"} className={"btn-primary btn rounded"}>Add card</Link>
                        </ButtonGroup>
                    </Card.Header>
                    <Card.Body>
                        <Accordion className={"text-black"}>
                            <Accordion.Item eventKey="0">
                                <Accordion.Header>Decks</Accordion.Header>
                                <Accordion.Body>
                                    {arr.map((data) => {
                                        var id = localStorage.getItem("userID");
                                        return(
                                            <ListGroup numbered key={data.id}>
                                                <ListGroup.Item
                                                    className="d-flex justify-content-between align-items-start">
                                                    <div className="ms-2 me-auto">
                                                        <div className="fw-bold">{data.name}</div>
                                                        {data.description}
                                                    </div>
                                                    <ButtonGroup>
                                                        <Button variant="danger"
                                                                onClick={() => this.onclick(data.id)}>
                                                            Delete
                                                        </Button>
                                                    </ButtonGroup>
                                                </ListGroup.Item>
                                            </ListGroup>
                                        )
                                    })
                                    }
                                </Accordion.Body>
                            </Accordion.Item>
                            <Accordion.Item eventKey="1">
                                <Accordion.Header>Cards</Accordion.Header>
                                <Accordion.Body>
                                    {
                                        arr2.map((card) => {
                                            return(
                                                <ListGroup numbered key={card.id}>
                                                    <ListGroup.Item
                                                        className="d-flex justify-content-between align-items-start">
                                                        <div className="ms-2 me-auto">
                                                            <div className="fw-bold">{card.word}</div>
                                                            {card.translation}
                                                        </div>
                                                        <ButtonGroup>
                                                            <Button variant="danger"
                                                                    onClick={() => this.onclick2(card.id)}>
                                                                Delete
                                                            </Button>
                                                        </ButtonGroup>
                                                    </ListGroup.Item>
                                                </ListGroup>
                                            )
                                        })
                                    }
                                </Accordion.Body>
                            </Accordion.Item>
                        </Accordion>
                    </Card.Body>
                </Card>
            </div>
        );
    }
}