# Apache License
# Version 2.0, January 2004
# Author: Eugene Tkachenko

from abc import ABC, abstractmethod

class Repository(ABC):
    
    @abstractmethod
    def post_comment_to_line(self, text, commit_id, file_path, line):
        pass
    
    @abstractmethod
    def post_comment_general(self, text):
        pass

class RepositoryError(Exception):
    pass