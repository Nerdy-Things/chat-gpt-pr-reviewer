# Apache License
# Version 2.0, January 2004
# Author: Eugene Tkachenko

import os
from openai import OpenAI
from ai.ai_bot import AiBot

class ChatGPT(AiBot):

    def __init__(self, token, model):
        self.__chat_gpt_model = model
        self.__client = OpenAI(api_key = token)

    def ai_request_diffs(self, code, diffs):
        stream = self.__client.chat.completions.create(
            messages=[
                {
                    "role": "user",
                    "content": AiBot.build_ask_text(code=code, diffs=diffs),
                }
            ],
            model = self.__chat_gpt_model,
            stream = True,
        )
        content = []
        for chunk in stream:
            if chunk.choices[0].delta.content:
                content.append(chunk.choices[0].delta.content)
        return " ".join(content)
    