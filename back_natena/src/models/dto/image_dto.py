from dataclasses import dataclass


@dataclass
class ImageDto:
    id: int
    url: str
    main: int  # Using int instead of bool to match Android expectations
    spot_id: int

    @classmethod
    def from_orm(cls, image) -> "ImageDto":
        return cls(
            id=image.id,
            url=image.url,
            main=1 if image.main else 0,  # Explicit integer conversion
            spot_id=image.spot_id
        )
