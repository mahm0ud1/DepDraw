import requests
import subprocess
import yaml

# Step 1: Obtain access token from Keycloak
# Replace the placeholders with actual values
keycloak_url = 'http://localhost:8080/realms/quarkus/protocol/openid-connect/token'
client_id = 'backend-service'
client_secret = 'secret'
username = 'admin'
password = 'admin'

payload = {
    'username': username,
    'password': password,
    'grant_type': 'password',
    'client_id': client_id,
    'client_secret': client_secret
}

response = requests.post(keycloak_url, data=payload)

if response.status_code == 200:
    access_token = response.json()['access_token']
    # print(access_token)
else:
    print('Failed to obtain access token from Keycloak')
    exit(1)



# Step 2: Clone the file from GitHub
# Replace the placeholders with actual values

def download_file(url, save_path):
    response = requests.get(url)
    response.raise_for_status()  # Check for any errors

    with open(save_path, 'wb') as file:
        file.write(response.content)


# Example usage
file_url = 'https://raw.githubusercontent.com/nemerna/KCM/main/realm.yaml'
save_file_path = './downloaded-realm.yaml'

download_file(file_url, save_file_path)

# subprocess.run(['git', 'clone', github_repo_url, destination_folder])

# Step 3: Read the file content
file_path = './downloaded-realm.yaml'

with open(file_path, 'r') as file:
    file_content = file.read()


# Parse the YAML content
data = yaml.safe_load(file_content)

# Retrieve the value of the 'realm' key
realm_value = data.get('realm')

# Step 4: Send file content to Quarkus application
# Replace the placeholder with your Quarkus application URL
quarkus_url = 'http://localhost:8082/{}/apply'.format(realm_value)
print(quarkus_url)
headers = {
    'Authorization': f'Bearer {access_token}',
    'Content-Type': 'text/plain'
}

response = requests.post(quarkus_url, headers=headers, data=file_content)

if response.status_code == 200:
    print('File content sent successfully to Quarkus application')
else:
    print('Failed to send file content to Quarkus application')
