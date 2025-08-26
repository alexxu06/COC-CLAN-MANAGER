import { Container, Row, Col, Image } from 'react-bootstrap';
import { useOutletContext } from 'react-router-dom';
import type { Member, OutletContextType } from "../types/index.ts";
import '../index.css';
import 'bootstrap/dist/css/bootstrap.min.css';


export default function GeneralMemberList() {
    const { memberList } = useOutletContext<OutletContextType>();

    const displayGeneral = memberList.map(member =>
        <Row key={member.tag}
            className="align-items-center border-bottom py-2"
            onClick={() => console.log('Row clicked!')} >
            <Col xs="auto" className='d-none d-sm-block'>
                <Image src={`/th${member.townHallLevel}.png`}
                    fluid
                    style={{ maxHeight: '50px', width: 'auto' }} />
            </Col>
            <Col xs={3}><strong>{member.name}</strong></Col>
            <Col xs={7}>
                <Row>
                    <Col xs={3} className='text-center'>
                        {member.clanRank}
                    </Col>
                    <Col xs={3} className='text-center'>
                        {member.role == "admin" ? "elder" : member.role}
                    </Col>
                    <Col xs={3} className='text-center'>
                        {member.donations}
                    </Col>
                    <Col xs={3} className='text-center'>
                        {member.trophies}
                    </Col>
                </Row>
            </Col>
        </Row>
    )

    return (
        <Container className="mt-sm-0 mt-4">
            <Row className="align-items-center border-bottom" >
                <Col xs="auto" className='d-none d-sm-block'>
                    <Image src="/th17.png"
                        fluid
                        style={{ maxHeight: '50px', width: 'auto' }}
                        className='invisible' />
                </Col>
                <Col xs={3}>Name</Col>
                <Col xs={7}>
                    <Row>
                        <Col xs={3} className='text-center'>Rank</Col>
                        <Col xs={3} className='text-center'>Role</Col>
                        <Col xs={3} className='text-center'>Donations</Col>
                        <Col xs={3} className='text-center'>Trophies</Col>
                    </Row>
                </Col>
            </Row>
            {displayGeneral}
        </Container>
    );
}