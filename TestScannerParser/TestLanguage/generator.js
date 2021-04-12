const fs = require("mz/fs");

async function main () {
    const content = (await fs.readFile("ast.json")).toString();
    const ast = JSON.parse(content);
    const js = generate(ast);
    await fs.writeFile("program.js",js);
}

function generate(ast) {
    let jsCode = ``;
    console.log(ast[0].body[0].left.value)
    ast[0].body.forEach(element => {
        jsCode+=generateExpression(element);
    })
    return jsCode;
}

function generateExpression(expr) {
   return `console.log(${expr.left.value + expr.operator + expr.right.value});`;
}

main();