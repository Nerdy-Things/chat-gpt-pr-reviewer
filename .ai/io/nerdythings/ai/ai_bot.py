# Apache License
# Version 2.0, January 2004
# Author: Eugene Tkachenko

from abc import ABC, abstractmethod
from ai.line_comment import LineComment

class AiBot(ABC):
    
    __no_response = "No critical issues found"
    __problems="errors, issues, potential crashes or unhandled exceptions"
    __chat_gpt_ask_long="""
We have an Android Jetpack project that follows the latest Google recommendations.
Could you describe briefly {problems} for the next code with given git diffs? 
Please, also, do not add intro words, just print errors in the format: "line_number : cause effect"
Line numbers should depend only on the code, not on the diffs.
If there are no {problems} just say "{no_response}".

Full code from the file:

{code}

GIT DIFFS:

{diffs}
"""

    @abstractmethod
    def ai_request_diffs(self, code, diffs) -> str:
        pass

    @staticmethod
    def build_ask_text(code, diffs) -> str:
        return AiBot.__chat_gpt_ask_long.format(
            problems = AiBot.__problems,
            no_response = AiBot.__no_response,
            diffs = diffs,
            code = code,
        )

    @staticmethod
    def is_no_issues_text(source) -> bool:
        target = AiBot.__no_response.replace(" ", "")
        it = iter(source)
        return all(c in it for c in target)
    
    @staticmethod
    def split_ai_response(input) -> list[LineComment]:
        if input is None or not input:
            return []
        
        lines = input.strip().split("\n")
        models = []

        for full_text in lines:
            number_str = ''
            number = 0
            full_text = full_text.strip()
            if len( full_text ) == 0:
                continue

            reading_number = True
            for char in full_text.strip():
                if reading_number:
                    if char.isdigit():
                        number_str += char
                    else:
                        break

            if number_str:
                number = int(number_str)

            models.append(LineComment(line = number, text = full_text))
        return models
    