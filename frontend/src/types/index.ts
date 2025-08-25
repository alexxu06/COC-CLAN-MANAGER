export interface Clan {
    tag: string;
    name: string;
    clanImg: string;
    description: string;
    memberList: Member[];
}

export interface Member {
    name: string;
    tag: string;
    role: string;
    donations: number;
    clanRank: number;
    townHallLevel: number;
    trophies: number;
    totalStars: number;
    totalPercentage: number;
    numAttacks: number;
    totalAttacks: number;
}