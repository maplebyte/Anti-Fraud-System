# Anti-Fraud System
JetBrains Academy. Project: [Anti-Fraud System](https://hyperskill.org/projects/232?track=12).

## About 
This project demonstrates (in a simplified form) the principles of anti-fraud systems in the 
For this project, we will work on a system with an expanded role model, a set of REST 
endpoints responsible for interacting with users, and an internal transaction validation logic based on a set of heuristic rules.

### The role model for system:</br>
<table>
<tr>
    <th></th>
    <th>Anonymous</th>
    <th>MERCHANT</th>
    <th>ADMINISTRATOR</th>
    <th>SUPPORT</th>
</tr>
<tr>
    <td>POST /api/auth/user</td>
    <td>+</td>
    <td>+</td>
    <td>+</td>
    <td>+</td>
</tr>
<tr>
    <td>DELETE /api/auth/user</td>
    <td>-</td>
    <td>-</td>
    <td>+</td>
    <td>-</td>
</tr>
<tr>
    <td>GET /api/auth/list</td>
    <td>-</td>
    <td>-</td>
    <td>+</td>
    <td>+</td>
</tr>
<tr>
    <td>POST /api/antifraud/transaction</td>
    <td>-</td>
    <td>+</td>
    <td>-</td>
    <td>-</td>
</tr>
<tr>
    <td>POST /api/auth/access</td>
    <td>-</td>
    <td>-</td>
    <td>+</td>
    <td>-</td>
</tr>
<tr>
    <td>POST /api/auth/role</td>
    <td>-</td>
    <td>-</td>
    <td>+</td>
    <td>-</td>
</tr>
<tr>
    <td>POST, DELETE, GET api/antifraud/suspicious-ip</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
    <td>+</td>
</tr>
<tr>
    <td>POST, DELETE, GET api/antifraud/stolencard</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
    <td>+</td>
</tr>
<tr>
    <td>GET /api/antifraud/history</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
    <td>+</td>
</tr>
<tr>
    <td>PUT /api/antifraud/transaction</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
    <td>+</td>
</tr>
</table>

ADMINISTRATOR is the user who has registered first, all other users</br>
should receive the MERCHANT roles. All users added after ADMINISTRATOR</br>
must be locked by default and unlocked later by ADMINISTRATOR.</br>
The SUPPORT role should be assigned by ADMINISTRATOR to one of</br>
the users later.

### Validation: 
* In the system  IP addresses will check for compliance with IPv4.</br>
Any address following this format consists of four series of numbers</br>
from 0 to 255 separated by dots.

* Card numbers must be checked according to the Luhn algorithm.

### Correlation to fraud detection rules:

* The transaction event correlate with the world region and the transaction date.</br>
The table for world region codes:

<table>
    <tr>
        <th>Code</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>EAP</td>
        <td>East Asia and Pacific</td>
    </tr>
    <tr>
        <td>ECA</td>
        <td>Europe and Central Asia</td>
    </tr>
    <tr>
        <td>HIC</td>
        <td>High-Income countries</td>
    </tr>
    <tr>
        <td>LAC</td>
        <td>Latin America and the Caribbean</td>
    </tr>
    <tr>
        <td>MENA</td>
        <td>The Middle East and North Africa</td>
    </tr>
    <tr>
        <td>SA</td>
        <td>South Asia</td>
    </tr>
    <tr>
        <td>SSA</td>
        <td>Sub-Saharan Africa</td>
    </tr>
</table>

A transaction containing a card number is PROHIBITED if:

1. There are transactions from more than 2 regions of the world other than the region</br>
of the transaction that is being verified in the last hour in the transaction history;

2. There are transactions from more than 2 unique IP addresses other than the IP of the</br>
transaction that is being verified in the last hour in the transaction history.

A transaction containing a card number is sent for MANUAL_PROCESSING if:

1. There are transactions from 2 regions of the world other than the region of the transaction</br>
that is being verified in the last hour in the transaction history;

2. There are transactions from 2 unique IP addresses other than the IP of the transaction</br>
that is being verified in the last hour in the transaction history.

### Adaptation mechanisms: feedback

Feedback carried out
manually by a SUPPORT specialist for completed transactions. Based on the feedback</br>
results, we will change the limits of fraud detection algorithms following the special rules.

<table>
    <tr>
        <th>Transaction Feedback →</br>
        Transaction Validity ↓</th>
        <th>ALLOWED</th>
        <th>MANUAL_PROCESSING</th>
        <th>PROHIBITED</th>
    </tr>
    <tr>
        <td>ALLOWED</td>
        <td>Exception</td>
        <td>↓ max ALLOWED</td>
        <td>↓ max ALLOWED
            </br>↓ max MANUAL</td>
    </tr>
    <tr>
        <th>MANUAL_PROCESSING</th>
        <td>↑ max ALLOWED</td>
        <td>Exception</td>
        <td>↓ max MANUAL</td>
    </tr>
    <tr>
        <th>PROHIBITED</th>
        <td>↑ max ALLOWED</br>
            ↑ max MANUAL</td>
        <td>↑ max MANUAL</td>
        <td>Exception</td>
    </tr>
    </tbody>
</table>

## Technologies 
WIP

## API
WIP