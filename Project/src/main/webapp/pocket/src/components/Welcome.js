import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

export default class Welcome extends React.Component{
    render() {
        const divStyle = {
            margin: "200px",
            padding: "20px",
            color: "white",
        };

        return (
            <div style={divStyle} className={"text-center"}>
                <h1>Pocket Lingo</h1>
                <p>
                    School project for memorizing foreign words based on the flashcard and periodic
                    learning repetition methods. Flashcard is an abstract two-sided card, the word in
                    the user’s native language is written on the hidden side, and the word in the
                    chosen language is written on another side.
                </p>
            </div>
        );
    }
}

