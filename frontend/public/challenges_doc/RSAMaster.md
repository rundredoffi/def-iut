# RSA Master<br><br>

## Introduction<br>

Bienvenue dans le défi "RSAMaster" !<br><br>

Vous êtes agent secret travaillant pour le MI6. Votre matricule est 008. Q, votre chef, vous a envoyé un message chiffré contenant le nom de votre prochaine cible. En effet, ce dernier mène des activités illégales et doit être neutralisé.<br><br>

Voici le message de Q à déchiffrer : <br><br>

```code
9ae509840a7301e5d63cb125bf57b31630ee92195856f94d55083b3202d2e7eff161bf2ec739
0178ee9561edafebc4f927742998eb2a44c44b2b8e3bf9ab36f63b7a930d101d9c3a1ec29a6f7
b562606ac6714a212b46dbc5680b11cc0d08c3f72e62bf7f815d84c9dcf316212aa38f3593946
f1ab0d617b4b6d59cecf9410d032eac0b7bddba9355e6dabbbd389ba5da8a6726a0578b5d3e
d9fe9c4dd69a432143b2da60229f3d0444eb547d27666b61d15d5e33b751a9686870dac5244
b94e957e217a07468c31ef944ccd5bf159dd3408ad98eea6b2b2e5ceffcf093437e8117e15696
5616b045d09cb4cebae484fbf9c8635e1ade34d29163db3d83fe211
```
<br><br>

Vous avez aussi une clé privée à votre disposition : <br><br>

```code
-----BEGIN RSA PRIVATE KEY-----
MIIEpQIBAAKCAQEAxJtrV7uw8itrqXHWnY2JMdjwgXKw+PDeFlNPsp/c1w7patuq
iiqaYbcrfb/fLF8FIU3MNqvSsMe3fX5w7YonnyrAmekl9crh2XEi0rSzWjiZN40y
MmZyrOrJLP/lb/ZD3jH0jK+vdEfhs9NZxvLUjOlvqgWrfFgICIrwWygxFmmU5EZg
RC3pGTVZypH3BiYRfVMV3rEuEJcczc5Pv77hLaPE+liLu6NlvcwaJYp98yH/fAuO
LmO3zXXIJ9mjSUoIZ7BnbUYtL7XZYBmM/NT4lBMzw4dD1nmIn71Fv+nquWYafEEA
+KStNndgxX60QzqlPcoCdPV+K+xDnYcCRKMZYwIDAQABAoIBAAje7lf2CwTSOrhZ
f5J6SV9rZ2af0yZYT9z4A1eO6Pr3HEcLBEXZqD+ScS7qUlqBKw0Wookw9X+uczQB
WX0OR71hw3DuWR0e+1PBTA3vw0vl09vyzfoCL1hn+43BOgv0M5m57UvPvX3kpGVl
ut5EJJz7B2PHf7ZLjok8I54pURWut5USW9P52mvXtQHru6ACeEmHz8JzN1iD3XcD
GDMl/3xi8XYMc0fOk1IQBAxNMOOD8vhd9rN9ZYg0q+H3MtkGShJXB2PfZmwS7rUb
+dQ7aEr0grFkpXCKejsvFheJlVLZnnVFT4rCnEXZ4JEKprFvH1ZAPAgy4/S6lDdC
/5f7L2kCgYEA7uukYrdSmhzRyW/Z96mmqZbF4U0RRvIvTIPrKaViGFX+en5OXdfg
G2cjlbwPCI6Yl0CK7f2ugoD8pPdL35sjdu6po9oyJWeCLYQzIn51QrXuNM2T/IVB
4rGdRbUnsF2Dn9w1SNRWtcAGw4UEl/UcwXdUrbvwmy7oNnlIce8BoD0CgYEA0qls
JmJgVrejS6WKldmArcQWJAKxUmhSWE6v4Oi9AAsexI4OC+4qCv9cwVU0BLLQWyHh
17tOAoOadfxt1asb0yDMb7FPkWTe2PXmVbKk7KNKg1dRI7gTIjGJ8XGM6Qc4VX6P
wt7yBZ7xkt63QUPUlMmGiOO09xtFPxvUAOH8mh8CgYEAzWrmtEVADOb4bEPx2OER
PUA3tEVJtIDnr9bAKtdZ5uiQ2oqcIE1KDuV3j/bXyhioE2tXx9VT5Dq4fbFtl7xQ
GqKKUil48fXz0+SU/nRRp2C6hMW4FeYQxrLFMLjXfeh5EcoIOizuvGd0qUhFscz5
w1ExCgrLjPEgkC1o3ySKdlUCgYEAwa1MJjoZ8oAhHKJUuJJgkOGPrYngNzB8TH4D
i1bOtOSEZBdqaKONedDvGTxZd9xiXPbYqTuma/9Mmh77aA9JpKEp86Jj0fuYOoZR
PwbgREOYdQppKdrJlI1REm7gKzodG9UhHPtWLb7EwNAMUW+oWSayjtYzAWDMGEpg
bnNWhucCgYEAsXbTFAJcVKOg+TiGugLuKsVy7hHIYaEZg8Ngj4/+1Y+v1Xzokduv
ftj3c+pt6x/9noGe7oynqKFdcUvb86X7V9otLF8oZHUIeknCTVy/tmLvv6kysN1O
wgLZUmln6nbjeIrc9eFpUG6klL3Qwel710jIKBs1OR7AjqkwwjlqfdM=
-----END RSA PRIVATE KEY-----
```

<br><br>

Vous avez le schéma de padding : <br><br>

```code
padding.PKCS1v15()
```
<br><br>

## Objectif<br>

Déchiffrer le message.

## Flag<br>

Le flag est une chaine de caractères à `XXXXXXXX`.

## Encouragements<br>

Bon courage !