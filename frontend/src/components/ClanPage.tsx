import axios from "axios";
import SearchBar from "./SearchBar.tsx";
import ClanInfo from "./ClanInfo";
import NavBar from './NavBar.tsx';
import { useEffect, useState } from "react";
import { Container, Row, Col, Spinner } from "react-bootstrap";
import type { Clan } from "../types"
import { Outlet, useParams } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';


export default function ClanPage() {
    const { clanTag } = useParams<{ clanTag: string }>();
    const [clan, setClan] = useState<Clan | null>(null);
    const [isLoading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        if (clanTag) {
            fetchClanInfo(clanTag);
        }
    }, [clanTag]);

    const fetchClanInfo = (tag: string): void => {
        setLoading(true);
        axios.get("/api/clan", { params: { tag: tag } })
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
        <Container className="mt-5">
            <Row>
                <Col lg={5} xs={12}>
                    <SearchBar />
                    <ClanInfo clan={clan} />
                </Col>
                <Col lg={7}  xs={12}>
                    <Container className="mt-sm-0 mt-4">
                        <NavBar />
                        {isLoading ? (
                            <div className="d-flex justify-content-center" >
                                <Spinner animation="border" className="mt-4"
                                    style={{ width: "9rem", height: "9rem", borderWidth: '0.7rem', color: 'rgba(0, 0, 0 , 0.2)' }} />
                            </div>) : (<Outlet context={{ memberList: clan?.memberList ?? [], clanTag: clan?.tag ?? "" }} />
                        )}
                    </Container>
                </Col>
            </Row>
        </Container >
    )
}
