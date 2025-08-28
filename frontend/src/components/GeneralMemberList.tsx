import { Container, Row, Col, Image } from 'react-bootstrap';
import { useOutletContext } from 'react-router-dom';
import type { OutletContextType } from "../types/index.ts";
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
            <Col xs={3} style={{ overflow: "hidden", overflowWrap: 'break-word' }}><b>{member.name}</b></Col>
            <Col xs={7}>
                <Row>
                    <Col xs={3} className='text-center'>
                        {member.clanRank}
                    </Col>
                    <Col xs={3} className='text-center' style={{ whiteSpace: 'nowrap' }}>
                        {member.role === "admin" ? "Elder" :
                            member.role === "coLeader" ? <>
                                <span className="d-none d-sm-inline">Co-leader</span>
                                <span className="d-inline d-sm-none">Co</span>
                            </> :
                                member.role === "leader" ? <>
                                    <span className="d-none d-sm-inline" style={{color: "#003366", WebkitTextStroke: "1px yellow"}}><b><i>Leader</i></b></span>
                                    <span className="d-inline d-sm-none" style={{color: "#003366", WebkitTextStroke: "1px yellow"}}><b><i>Lead</i></b></span>
                                </> :
                                    member.role === "member" ? <>
                                        <span className="d-none d-sm-inline">Member</span>
                                        <span className="d-inline d-sm-none">Mem</span>
                                    </> :
                                        member.role}
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
                        <Col xs={3} className='d-flex align-items-center text-center'>Rank</Col>
                        <Col xs={3} className='text-center'>Role</Col>
                        <Col xs={3} className='text-center'>
                            <span className="d-none d-sm-inline">Donations</span>
                            <span className="d-inline d-sm-none">Don</span>
                        </Col>
                        <Col xs={3} className='text-center'>Trophies</Col>
                    </Row>
                </Col>
            </Row>
            <div className="container-fluid d-none d-lg-flex flex-column" style={{ height: "75vh", overflowY: "auto" }}>
                {displayGeneral}
            </div>

            <div className="container-fluid d-lg-none">
                {displayGeneral}
            </div>

        </Container>
    );
}