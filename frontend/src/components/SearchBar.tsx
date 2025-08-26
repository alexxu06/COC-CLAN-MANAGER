import { useState } from "react";
import { Form, Button, Row, Col } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

export default function SearchBar() {
    const [clanTag, setClanTag] = useState<String>("");
    const navigate = useNavigate();

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        navigate(`/${clanTag}/general`);
    }

    return (
        <Form onSubmit={handleSubmit} >
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