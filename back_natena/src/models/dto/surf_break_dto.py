from dataclasses import dataclass


@dataclass
class SurfBreakDTO:
    id: int
    type: str

    @classmethod
    def extractSurfBreakFromDB(cls, surfBreak):
        return cls(
            id = surfBreak.id,
            type = surfBreak.type
        )

