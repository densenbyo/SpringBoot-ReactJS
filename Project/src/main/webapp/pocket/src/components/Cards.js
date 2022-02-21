import React, {Component} from "react";
import {Button, ButtonGroup, Card, Carousel, ListGroup} from "react-bootstrap";
import RepoService from "../services/repoService";

var Background = require('./csm_2162_PE_Dekorbild_0ec3e17e00.jpg');

export default class Cards extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cards:[],
            mouse:false
        }
    }

    componentDidMount() {
        this.getCards();
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

    mouseIsOn = () => {
        this.setState({mouse:true});
    }

    mouseIsOut = () => {
        this.setState({mouse:false});
    }

    render() {
        return (
            <div>
                <Card className={"border border-dark bg-dark text-white"}>
                    <Card.Body>
                        ID - {localStorage.getItem("userID")}
                        { this.state.cards.length === 0 ?
                            <div>
                                It's better to add a new card, bro.
                            </div> :
                            <div>
                                <Carousel interval={null} >
                                    {this.state.cards.map((card) => {
                                        return (
                                                <Carousel.Item key={card.id}>
                                                    <img height={'500px'} width={'100%'} src={Background}/>
                                                    <Carousel.Caption>
                                                        <h3>{card.word}</h3>
                                                        <div onMouseEnter={this.mouseIsOn} onMouseLeave={this.mouseIsOut}
                                                             style={{justifyContent:'center', alignItems:'center'}}>
                                                            {this.state.mouse ?
                                                                <p>{card.translation}</p>
                                                                : <p>Show Translation</p>
                                                            }
                                                        </div>
                                                    </Carousel.Caption>
                                                </Carousel.Item>

                                        )
                                    })
                                    }
                                </Carousel>
                            </div>
                        }
                    </Card.Body>
                </Card>
            </div>
        );
    }
}