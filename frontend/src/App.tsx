import SearchBar from "./components/SearchBar";
import ClanInfo from "./components/ClanInfo";
import MemberList from "./components/MemberList";
import { useState } from "react";
import { Container, Row, Col } from "react-bootstrap";
import { type Clan } from "./types"
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const [clan, setClan] = useState<Clan | null>(null);

  return (
    <Container className="mt-5">
      <Row>
        <Col md={5}>
            <SearchBar setClan={setClan}/>
            <ClanInfo clan={clan}/>
        </Col>
        <Col md={7}>
          <MemberList memberList={clan?.memberList ?? []} />
        </Col>
      </Row>
    </Container>
  )
}

export default App
