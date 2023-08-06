# Authentication Service

## How Tos

### Self signed certificate creation

```bash
openssl req -x509 \
  -nodes -days 365 -newkey rsa:2048 -keyout local-testing.key \
  -out local-testing.crt \
  -subj '/C=NL/ST=Utrecht/L=Utrecht/CN=eu.andreschepers.local-testing-cert'
```

