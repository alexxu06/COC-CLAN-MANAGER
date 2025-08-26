import axios from "axios";
import { type Clan } from "../types"
import { useState } from "react";
import { Form, Button, Row, Col } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

type SearchBarProps = {
    setClan: React.Dispatch<React.SetStateAction<Clan | null>>;
    setLoading: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function SearchBar({ setClan, setLoading }: SearchBarProps) {
    const [clanTag, setClanTag] = useState<String>("");

    const fetchClanInfo = (event: React.FormEvent): void => {
        event.preventDefault();
        setLoading(true);
        axios.get("/api/clan", { params: { tag: clanTag } })
            .then((response) => {
                setClan(response.data);
                setLoading(false);
            })
            .catch((error) => {
                if (error.response.status == 404) {
                    alert("Clan Not Found");
                } else {
                    alert("System Error");
                }
                console.log(error);
                setLoading(false);
            });
    }

    return (
        <Form onSubmit={fetchClanInfo} >
            <Form.Group controlId="clanTag">
                <Row className="align-items-center gx-2" >
                    <Col xs="auto">
                        <Form.Label className="mb-0">Clan Tag:</Form.Label>
                    </Col>
                    <Col>
                        <Form.Control
                            type="text"
                            placeholder="Insert Clan Tag Here"
                            onChange={(e) => setClanTag(e.target.value)} />
                    </Col>
                    <Col xs="auto">
                        <Button type="submit">Search</Button>
                    </Col>
                </Row>
            </Form.Group>
        </Form>
    );
}