import { Container, Nav } from 'react-bootstrap';
import { Link, useLocation } from 'react-router-dom';
import '../index.css';
import 'bootstrap/dist/css/bootstrap.min.css';

export default function NavBar() {
  const location = useLocation();

  return (
    <Container className="mt-sm-0 mt-4">
      <Nav variant="tabs" activeKey={ location.pathname }>
        <Nav.Item>
          <Nav.Link as={Link} to="/general" eventKey="/general">General</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link as={Link} to="/war" eventKey="/war">War</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link eventKey="disabled" disabled>
            Clan Capital
          </Nav.Link>
        </Nav.Item>
      </Nav>
    </Container>
  );
}