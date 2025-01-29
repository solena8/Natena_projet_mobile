from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy_utils import database_exists, create_database
from contextlib import contextmanager


class DatabaseManager:
    _instance = None
    _engine = None
    _Session = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(DatabaseManager, cls).__new__(cls)
            cls._instance._initialize()
        return cls._instance

    def _initialize(self):
        self._engine = create_engine("sqlite:///surfdatabase.db", echo=True)

        if not database_exists(self._engine.url):
            create_database(self._engine.url)

        self._Session = sessionmaker(bind=self._engine)

    @contextmanager
    def get_session(self):
        session = self._Session()
        try:
            yield session
            session.commit()
        except Exception as e:
            session.rollback()
            raise e
        finally:
            session.close()


# Instance unique globale
db = DatabaseManager()
