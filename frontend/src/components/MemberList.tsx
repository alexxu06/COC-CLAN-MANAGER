import { Container, Nav, Row, Col, Image } from 'react-bootstrap';
import { type Member } from "../types/index.ts";
import '../index.css';
import 'bootstrap/dist/css/bootstrap.min.css';

type MemberListProp = {
  memberList: Member[];
}

export default function MemberList({ memberList }: MemberListProp) {

  const calculateAverage = (total: number, attacks: number) => {
    let average: number = Math.round(total / attacks * 100)
    return isNaN(average) ? 0 : average;
  }

  const displayMembers = memberList.map(member =>
    <Row key={member.tag}
      className="align-items-center border-bottom py-2 hover-row"
      onClick={() => console.log('Row clicked!')} >
      <Col xs="auto" className='d-none d-sm-block'>
        <Image src="./public/th17.png"
          fluid
          style={{ maxHeight: '50px', width: 'auto' }} />
      </Col>
      <Col xs={3}><strong>{member.name}</strong></Col>
      <Col xs={2} className='text-center'>
        {calculateAverage(member.totalStars, member.totalAttacks) / 100}
      </Col>
      <Col xs={3} className='text-center'>
        {calculateAverage(member.totalPercentage, member.totalAttacks) / 100}%
      </Col>
      <Col xs={2} className='text-center'>
        {calculateAverage(member.totalAttacks, member.totalAttacks)}%
      </Col>
    </Row>
  );

  return (
    <Container className="mt-sm-0 mt-4">
      <Nav variant="tabs" defaultActiveKey="/home">
        <Nav.Item>
          <Nav.Link href="/home">General</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link eventKey="link-1">War</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link eventKey="disabled" disabled>
            Clan Capital
          </Nav.Link>
        </Nav.Item>
      </Nav>
      <Row className="align-items-center border-bottom" >
        <Col xs="auto" className='d-none d-sm-block'>
          <Image src="./public/th17.png"
            fluid
            style={{ maxHeight: '50px', width: 'auto' }}
            className='invisible' />
        </Col>
        <Col xs={3}>Name</Col>
        <Col xs={2} className='text-center'>Avg Stars</Col>
        <Col xs={3} className='text-center'>Avg Destruction</Col>
        <Col xs={2} className='text-center'>Hit Rate</Col>
      </Row>
      {displayMembers}
    </Container>
  );
}