<template>
  <div class="about-container">
    <div class="content">
      <h1 class="title">Over deze app</h1>
      <p class="intro">
        Deze notitie-app is ontworpen voor mensen die georganiseerd willen blijven en snel hun gedachten willen vastleggen.
        Of je nu een student bent, een professional, of gewoon iemand die graag orde houdt: deze app helpt je om je ideeën
        eenvoudig te structureren.
      </p>

      <section class="section">
        <h2>🔍 Belangrijkste functies</h2>
        <ul>
          <li>📝 Maak, bewerk en verwijder notities</li>
          <li>🏷️ Voeg tags toe om je notities te categoriseren</li>
          <li>🌙 Donker & licht thema voor oogcomfort</li>
          <li>📁 Archiveer oude notities zonder ze te verwijderen</li>
          <li>🔎 Gebruik de zoekfunctie om snel notities te vinden</li>
        </ul>
      </section>

      <section class="section">
        <h2>👨‍💻 Over de maker</h2>
        <p>
          Deze applicatie is ontwikkeld door Melle, Samuel en Tarik als een persoonlijk project voor het oefenen van moderne webtechnologieën
          zoals <strong>Vue.js</strong>, <strong>Tailwind CSS</strong> en componentgebaseerd design. Het doel is om een intuïtieve,
          snelle en mooie gebruikerservaring te bieden.
        </p>
      </section>

      <router-link to="/" class="home-btn">⬅️ Terug naar home</router-link>
    </div>
  </div>
</template>

<style scoped>
.about-container {
  padding: 3rem 1.5rem;
  display: flex;
  justify-content: center;
  font-family: 'Inter', sans-serif;
}

.content {
  max-width: 800px;
  width: 100%;
  background: var(--color-card, #fff);
  padding: 2.5rem;
  border-radius: var(--radius-md, 16px);
  box-shadow: var(--shadow-lg, 0 4px 16px rgba(0, 0, 0, 0.1));
  color: #333;
}

.title {
  font-size: 2.5rem;
  margin-bottom: 1.5rem;
  font-weight: 700;
}

.intro {
  font-size: 1.125rem;
  margin-bottom: 2rem;
  line-height: 1.6;
}

.section {
  margin-bottom: 2rem;
}

.section h2 {
  font-size: 1.5rem;
  margin-bottom: 0.75rem;
  color: #111;
}

.section ul {
  list-style: none;
  padding: 0;
}

.section li {
  margin-bottom: 0.5rem;
  font-size: 1.05rem;
}

.home-btn {
  display: inline-block;
  margin-top: 1rem;
  background: #000;
  color: #fff;
  padding: 0.75rem 1.25rem;
  border-radius: var(--radius-sm, 10px);
  text-decoration: none;
  transition: background 0.3s ease;
}

.home-btn:hover {
  background: #333;
}
</style>