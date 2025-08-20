import "./searchbar.css";
import axios from "axios";
import { useState } from "react";
import Button from 'react-bootstrap/Button';

export default function SearchBar() {
  const [thing, setThing] = useState("asdas");
  
    const test = () => {
        axios.get("/api/clan", {
        params: {
            tag: "2GLY9GRG9"
        }
        })
        .then(function (response) {
            console.log(response);
            setThing(response.data);
        })
        .catch(function (error) {
            console.log(error);
            alert(error.response.data);
        });
    }
  
    return(
        <div>
            <>
                <Button onClick={test} variant="primary">Primary</Button>
                <Button variant="secondary">Secondary</Button>
                <Button variant="success">Success</Button>
                <Button variant="warning">Warning</Button>
                <Button variant="danger">Danger</Button>
                <Button variant="info">Info</Button>
                <Button variant="light">Light</Button>
                <Button variant="dark">Dark</Button>
                <Button variant="link">Link</Button>
            </>
        </div>
    );
}