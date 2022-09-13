import {CSSProperties} from "react";

function NotFound() {
    return (
        <section className="not_found">
            <h1 style={h1Style}>404</h1>
        </section>
    )
}

const h1Style: CSSProperties = {
    textAlign: "center",
    fontSize: "32px",
}


export default NotFound;