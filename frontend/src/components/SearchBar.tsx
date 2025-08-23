import axios from "axios";
import { useState } from "react";
import { Form, Button, Container } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

export default function SearchBar() {
    const [memberNames, setMemberNames] = useState<String[]>([]);
    const [clanTag, setClanTag] = useState<String>("");

    const fetchClanInfo = (event: React.FormEvent) => {
        event.preventDefault();
        axios.get("/api/clan", {params: { tag: clanTag } })
            .then((response) => {
                const { name } = response.data;
                const { memberList } = response.data;
                const names = memberList.map((member: any) => member.name);
                
                setMemberNames(names);
            })
            .catch((error) => {
                console.log(error);
                alert(error.response.data);
            });
    }

    return (
        <Container>
            <Form onSubmit={fetchClanInfo}>
                <Form.Group controlId="clanTag">
                    <Form.Label>Clan Tag</Form.Label>
                    <Form.Control 
                    type="text" 
                    name="clanTag"
                    placeholder="Insert Clan Tag Here" 
                    onChange={(e) => setClanTag(e.target.value)}/>
                </Form.Group>
                <Button type="submit">Search</Button>
            </Form>

            <ul>
                {memberNames.map((name, index) => <li key={index}>{name}</li>)}
            </ul>
        </Container>

    );
}