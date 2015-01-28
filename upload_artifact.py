from os import environ as env
from glob import glob

import datetime

FILENAME = 'build/libs/WhoWas.jar'

def main():
    client = dropbox.client.DropboxClient(env.get('DROPBOX_ACCESS_TOKEN'))
    base = FILENAME.split('/')[2].split('.jar')[0]
    date = datetime.datetime.now().strftime("%Y-%m-%d")
    build_id = env['TRAVIS_JOB_NUMBER']

    with open(FILENAME) as f:
        client.put_file('/' + base + '-' + date + '-' + build_id, f, overwrite=True)
    print("Successfully uploaded latest build to Dropbox")


if __name__ == "__main__":
    main()
