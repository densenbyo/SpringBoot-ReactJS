import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';

export default class NotFound extends React.Component {
    render() {
        const divStyle = {
            margin: "200px",
            padding: "20px",
            color: "white",
        };

        return (
            <div style={divStyle} className={"text-center"}>
                <h3 >NOT FOUND</h3>
                <p>
                    Page you are looking for is not found!
                </p>
            </div>
        );
    }
}