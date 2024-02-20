# Apache License
# Version 2.0, January 2004
# Author: Eugene Tkachenko

import os
from log import Log

class EnvVars:
    def __init__(self):
        self.owner = os.getenv('REPO_OWNER')
        self.repo = os.getenv('REPO_NAME')
        self.pull_number = os.getenv('PULL_NUMBER')
        
        self.token = os.getenv('GITHUB_TOKEN') 

        self.base_ref = os.getenv('GITHUB_BASE_REF') 
        self.head_ref = os.getenv('GITHUB_HEAD_REF') 

        self.chat_gpt_token = os.getenv('CHATGPT_KEY') 
        self.chat_gpt_model = os.getenv('CHATGPT_MODEL') 

        self.target_extensions = os.getenv('TARGET_EXTENSIONS')
        self.target_extensions = [lang.strip() for lang in self.target_extensions.split(",")]

        if len(self.target_extensions) == 0:
            raise ValueError(f"Please specify TARGET_EXTENSIONS. Coma separated, could be, like: kt,java,py,js,swift,c,h. Only these files will be reviewed")

        self.env_vars = {
            "owner" : self.owner,
            "repo" : self.repo,
            "token" : self.token,
            "base_ref" : self.base_ref,
            "pull_number" : self.pull_number,
            "chat_gpt_token" : self.chat_gpt_token,
            "chat_gpt_model" : self.chat_gpt_model,
        }

    def check_vars(self):
        missing_vars = [var for var, value in self.env_vars.items() if not value]
        if missing_vars:
            missing_vars_str = ", ".join(missing_vars)
            raise ValueError(f"The following environment variables are missing or empty: {missing_vars_str}")
        else:
            Log.print_green("All required environment variables are set.")
        