import SearchBar from "./components/SearchBar";
import ClanInfo from "./components/ClanInfo";
import MemberList from "./components/MemberList";
import { Container, Row, Col } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {

  return (
    <Container>
      <Row>
        <Col>
            <SearchBar />
            <ClanInfo />
        </Col>
        <Col>
          <MemberList />
        </Col>
      </Row>
    </Container>
  )
}

export default App
