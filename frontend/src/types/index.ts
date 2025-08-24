export interface Clan {
    tag: string;
    name: string;
    memberList: Member[];
}

export interface Member {
    name: string;
    tag: string;
    donations: number;
    clanRank: number;
    totalStars: number;
    totalPercentage: number;
    numAttacks: number;
    totalAttacks: number;
}