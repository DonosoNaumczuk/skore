import React from 'react'

    const Accounts = ( {account} ) => {
      return (
        <div>
          <center><h1>Accounts</h1></center>
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">{account.username}</h5>
                <h6 className="card-subtitle mb-2 text-muted">{account.email}</h6>
              </div>
            </div>
        </div>
      )
    };

    export default Accounts;