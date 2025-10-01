package org.raden.server;

public class Preview {
    public static String html() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Reminder</title>
                <script src="https://cdn.tailwindcss.com"></script>
                <style>
                    body {
                        overflow: hidden;
                    }
                </style>
            </head>
            <body class="bg-black flex items-center justify-center min-h-screen">
                <canvas id="fireworksCanvas" class="absolute top-0 left-0 w-full h-full z-0"></canvas>

                <div id="datetime" class="relative z-10 text-center font-mono text-white">
                    <div id="time" class="text-6xl md:text-8xl font-bold tracking-widest text-shadow-lg"></div>
                    <div id="date" class="text-xl md:text-2xl mt-2 tracking-wider"></div>
                </div>

                <script>
                    const timeElement = document.getElementById('time');
                    const dateElement = document.getElementById('date');

                    function updateDateTime() {
                        const now = new Date();
                        const hours = String(now.getHours()).padStart(2, '0');
                        const minutes = String(now.getMinutes()).padStart(2, '0');
                        const seconds = String(now.getSeconds()).padStart(2, '0');
                        const colon = seconds % 2 === 0 ? '<span class="opacity-50">:</span>' : ':';
                        timeElement.innerHTML = `${hours}${colon}${minutes}${colon}${seconds}`;
                        const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
                        dateElement.textContent = now.toLocaleDateString('en-US', options);
                    }

                    setInterval(updateDateTime, 1000);
                    updateDateTime();

                    const canvas = document.getElementById('fireworksCanvas');
                    const ctx = canvas.getContext('2d');
                    canvas.width = window.innerWidth;
                    canvas.height = window.innerHeight;

                    let particles = [];
                    let fireworks = [];

                    function randomColor() {
                        const hue = Math.random() * 360;
                        return `hsl(${hue}, 100%, 50%)`;
                    }

                    class Particle {
                        constructor(x, y, color, velocity) {
                            this.x = x;
                            this.y = y;
                            this.color = color;
                            this.velocity = velocity;
                            this.alpha = 1;
                            this.friction = 0.99;
                            this.gravity = 0.03;
                        }

                        draw() {
                            ctx.save();
                            ctx.globalAlpha = this.alpha;
                            ctx.beginPath();
                            ctx.arc(this.x, this.y, 2, 0, Math.PI * 2, false);
                            ctx.fillStyle = this.color;
                            ctx.fill();
                            ctx.restore();
                        }

                        update() {
                            this.velocity.x *= this.friction;
                            this.velocity.y *= this.friction;
                            this.velocity.y += this.gravity;
                            this.x += this.velocity.x;
                            this.y += this.velocity.y;
                            this.alpha -= 0.005;
                            this.draw();
                        }
                    }

                    class Firework extends Particle {
                         constructor(x, y, color) {
                            super(x, y, color, {
                                x: (Math.random() - 0.5) * 3,
                                y: Math.random() * -12 - 5
                            });
                            this.explosionParticles = 100;
                         }

                         update() {
                            super.update();
                            if (this.velocity.y >= 0) {
                                this.explode();
                                this.alpha = 0;
                            }
                         }

                         explode() {
                            for (let i = 0; i < this.explosionParticles; i++) {
                                const angle = Math.random() * Math.PI * 2;
                                const speed = Math.random() * 5;
                                particles.push(new Particle(this.x, this.y, this.color, {
                                    x: Math.cos(angle) * speed,
                                    y: Math.sin(angle) * speed,
                                }));
                            }
                         }
                    }

                    function animate() {
                        requestAnimationFrame(animate);
                        ctx.fillStyle = 'rgba(0, 0, 0, 0.1)';
                        ctx.fillRect(0, 0, canvas.width, canvas.height);

                        if (Math.random() < 0.03) {
                             fireworks.push(new Firework(Math.random() * canvas.width, canvas.height, randomColor()));
                        }

                        fireworks.forEach((firework, index) => {
                            if (firework.alpha <= 0) {
                                fireworks.splice(index, 1);
                            } else {
                                firework.update();
                            }
                        });

                        particles.forEach((particle, index) => {
                            if (particle.alpha <= 0) {
                                particles.splice(index, 1);
                            } else {
                                particle.update();
                            }
                        });
                    }

                    window.addEventListener('resize', () => {
                         canvas.width = window.innerWidth;
                         canvas.height = window.innerHeight;
                    });
                   \s
                    const timeCheckInterval = setInterval(() => {
                        if (new Date().getHours() === 17 && new Date().getMinutes() === 30) {
                            animate();`
                           \s
                            const absenKocak = document.createElement('div');
                            absenKocak.textContent = "Absen Kocak!";
                            absenKocak.className = "absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-4xl md:text-6xl font-extrabold text-red-400 bg-black bg-opacity-25 p-4 rounded-lg shadow-lg z-20 animate-pulse";
                            document.body.appendChild(absenKocak);
                           \s
                            clearInterval(timeCheckInterval);
                        }
                    }, 3000);
                </script>

            </body>
            </html>
  \s""".trim();
    }
}
